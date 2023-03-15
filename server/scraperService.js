const rp = require('request-promise')
const cheerio = require('cheerio')

// Regex for Salaries
const salaryRegex = /(?:USD)?\s*(\$|\€|\£|\¥)\s*(\d{1,3}(?:,\d{3})*|\d+)\s*(k|thousand|million|hundred|\/h)?/i;

// Array with Most-Common Job Types
const jobTypes = ["Part-Time", "Full-Time", "Internship", "Contract"]

// Array with Most-Common Seniority Types
const seniority = ["Senior/Lead", "Senior", "Mid", "Mid Level", "Sr.", "Junior", "Jr.", "Mentor", "Lead", "break"]
const stacks = ["Java", "JavaScript", "Python", "React", "Node", "NodeJS", "Node.js", "NextJS",
            "MySQL", "SQL", "MongoDB", "Redis", "ReactJS", "PostgreSQL", "Heroku", "Ruby",
            "Ruby on Rails", "Typescript", "Apache", "Spark", "Vue.js", "Rails", "GraphQL",
            "Azure", "Data", "AWS", "Debugging", "Documentation", "Django", "Flask", "PHP",
            "Xamarin", ".NET", "C#", "Android", "Kotlin", "Salesforce"]

// Array with most common regions
const countryRegions = ["Brazil", "UK", "EU", "Europe", "US"]

/**
 * This function scraps the WWR website and returns the current jobs
 *
 * @param {String} path - Path of the website
 * @returns {[]Object} - An array of objects containing each job oportunity data
 */
async function handleWWRQuery(path) {
    const wwrURL = new URL("https://weworkremotely.com")
    const locations = ["Anywhere in the World", "USA Only", "Europe Only", "UK Only",
                        "Latin America Only", "North America Only", "Americas Only"]
    const separators = [" - ", " (", ": ", ", "]

    let jobURLList = []
    let jobInfoList = []

    wwrURL.pathname = path

    await rp(wwrURL.href).then(html => {
        let $ = cheerio.load(html)

        for(let i = 1; i <= 51; i++) {
            curArticle = $('article > ul > li > a', html)[i]
            if (curArticle !== undefined) {
                curHref = curArticle.attribs.href
                if (! curHref.includes("listings") && curHref != "/") {
                   jobURLList.push(curHref)
                }
            }
        }
    })

    await Promise.all(jobURLList.map(async pathName => {
        wwrURL.pathname = pathName
        html = await rp(wwrURL.href)
        let jobInfoJSON = {
            "seniority": null,
            "salary": null,
            "jobType": null,
            "jobTitle": null,
            "stack": null,
            "company": null,
            "location": null
        }
        let $ = cheerio.load(html)

        let listingTags = $("span.listing-tag").text() // Gets Job Type, Location and Tech
        let stackList = []
        
        jobTypes.forEach(type => {
            if (listingTags.includes(type)) {
                jobInfoJSON.jobType = type
            }
        })

        let listingHeader = $(".listing-header-container > h1").text() // Gets Job Title, Location, Seniority, Tech
        let jobTitle = listingHeader.replace("Remote", "")

        seniority.forEach(sr => {
            jobTitle = jobTitle.replace(sr+" ", "")
            while (jobTitle[0] == "/" || jobTitle[0] == "-" || jobTitle[0] == " " ) {
                jobTitle = jobTitle.substring(1)
            }
        })

        separators.forEach(str => {
            jobTitle = jobTitle.split(str)[0]
        })

        jobInfoJSON.jobTitle = jobTitle

        let monetaryValues = []
        sectionJobDiv = $("section.job > div > div").text() // Maybe gets Salary and Tech
        sectionJobLi = $("section.job > div > ul > li").text() // Last chance for getting Salary and Tech

        matchSectionJobDiv = salaryRegex.exec(sectionJobDiv)
        matchSectionJobLi = salaryRegex.exec(sectionJobLi)

        if (matchSectionJobDiv != null) {
            monetaryValues.push(matchSectionJobDiv[0])
        }
        if (matchSectionJobLi != null) {
            monetaryValues.push(matchSectionJobLi[0])
        }
        jobInfoJSON.salary = monetaryValues

        seniority.forEach(sr => {
            if(listingHeader.includes(sr)) {
                if (sr == "Sr.") {
                    jobInfoJSON.seniority = "Senior"
                } else if (sr == "Jr.") {
                    jobInfoJSON.seniority = "Junior"
                } else if (sr == "Mif") {
                    jobInfoJSON.seniority = "Mid Level"
                } else {
                    jobInfoJSON.seniority = sr
                }
            } else if (sr == "break" && jobInfoJSON.seniority == null) {
                jobInfoJSON.seniority = "Mid Level"
            }
        })

        locations.forEach(location => {
            if (listingTags.includes(location)) {
                jobInfoJSON.location = location
            }
        })

        countryRegions.forEach(region => {
            if(listingHeader.includes(region)) {
                jobInfoJSON.location = region
            }
        })

        stacks.forEach(tech => {
            allText = listingTags+listingHeader+sectionJobDiv+sectionJobLi
            if ( allText.includes(tech) && !stackList.includes(tech)) {
                if (tech == "Java") {
                    if (!allText.includes("JavaScript")) {
                        stackList.push(tech)
                    }
                } else {
                    stackList.push(tech)
                }
            }
        })
        jobInfoJSON.stack = stackList

        companyCard = $(".company-card > h2 > a").text()
        jobInfoJSON.company = companyCard
        jobInfoList.push(jobInfoJSON)
    }))
    return jobInfoList
}

// WWR Queries
wwrQueriesArray = []
wwrQueriesArray.push(handleWWRQuery("categories/remote-full-stack-programming-jobs")) // Full Stack Dev
wwrQueriesArray.push(handleWWRQuery("categories/remote-front-end-programming-jobs")) // Front End Dev
wwrQueriesArray.push(handleWWRQuery("categories/remote-back-end-programming-jobs")) // Back End Dev
wwrQueriesArray.push(handleWWRQuery("categories/remote-devops-sysadmin-jobs")) // Dev OPs

/**
 * TODO:
 *
 * - Allign all this logic in a function
 * - Write the collected data to a Database (or at least a JSON file for now)
 * - Improve documentation of the code
 */
wwrresults = Promise.all(wwrQueriesArray).then(arr => {
    let finalArray = []
    for (let i of arr) {
        finalArray = finalArray.concat(i)
    }
    console.log(finalArray)
})